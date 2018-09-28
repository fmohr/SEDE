package de.upb.sede.config;

import de.upb.sede.util.FileUtil;
import de.upb.sede.util.Iterators;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceSpecTransformer {

	private String classConfPath;
	private String exportDir;
	/*
	 * flag that determines if swing gui should be used.
	 */
	private boolean gui;



	public static void main(String... args) throws IOException, InvocationTargetException, InterruptedException {
		File workingDir = new File(new File(".").getCanonicalPath());
		ServiceSpecTransformer transformer = new ServiceSpecTransformer();

		System.out.println("Passed arguments: " + Arrays.toString(args));

		if(args.length == 2) {
			transformer.gui = false;
			transformer.classConfPath = args[0];
			transformer.exportDir = args[1];
		} else {
			transformer.gui = true;
			EventQueue.invokeAndWait(() -> {
				JFileChooser jfc = new JFileChooser(workingDir);
				jfc.setDialogTitle("Select a class configuration:");
//					jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
//					jfc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("classes config files", "json");
				jfc.addChoosableFileFilter(filter);
				int returnValue = jfc.showOpenDialog(null);
				if (returnValue != JFileChooser.APPROVE_OPTION) {
					System.out.println("Error: No class config chosen.");
					System.exit(1);
				}
				transformer.classConfPath = jfc.getSelectedFile().getPath();
			});
			EventQueue.invokeAndWait(() -> {
				File f = new File(transformer.classConfPath);
				JFileChooser jfc = new JFileChooser(f.getParentFile());
				jfc.setDialogTitle("Select an export folder:");
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnValue = jfc.showDialog(null, "export");
				if (returnValue != JFileChooser.APPROVE_OPTION) {
					transformer.exportDir = "servicespec/";
				} else {
					transformer.exportDir = jfc.getSelectedFile().getPath();
				}
			});
		}
		if(!transformer.exportDir.endsWith(File.separator)) {
			transformer.exportDir += File.separator; // add file seperator at the end.
		}

		System.out.println("Class config file path: " + transformer.classConfPath);
		System.out.println("Export directory: " + new File(transformer.exportDir).getAbsolutePath());

		try{
			transformer.transform();
			if(transformer.gui) {
				/*
				 * JFileChooser was shown. Now notify end message per gui:
				 */
				EventQueue.invokeAndWait(() ->
						JOptionPane.showMessageDialog(null,"Transformation has finished.")
				);
			} else {
				/*
				 * Just print the end message to console:
				 */
				System.out.println("Transformation has finished.");
			}
		} catch(Exception e) {
			if(transformer.gui) {
				StringBuilder sb = new StringBuilder(e.toString());
				for (StackTraceElement ste : e.getStackTrace()) {
					sb.append("\n\tat ");
					sb.append(ste);
				}
				JTextArea jta = new JTextArea(sb.toString());
				JScrollPane jsp = new JScrollPane(jta){
					@Override
					public Dimension getPreferredSize() {
						return new Dimension(480, 320);
					}
				};
				JOptionPane.showMessageDialog(
						null, jsp, "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				e.printStackTrace(System.err);
			}
		}
	}

	public void transform() {
		File classConf = new File(classConfPath);

		if(classConf.isDirectory()) {
			/*
			 * iterate each class configuration in the given folder:
			 */
			for(String pathToClassConf : FileUtil.listAllFilesInDir(classConfPath, "(.*?)classconf\\.json$")) {
				String exportDir = this.exportDir + pathToClassConf.substring(0, pathToClassConf.length()-14) +"/";
				transform(exportDir, classConfPath + pathToClassConf);
			}
		} else if (classConf.isFile() && classConfPath.endsWith(".json")) {
			/*
			 * the given configuration is a class configuration by itself.
			 */
			transform(exportDir, classConfPath);
		} else {
			System.err.println("Transformation failed due to errors in class conf path: " + classConfPath);
		}

	}

	public void transform(String exportDir, String classConfPath) {
		ClassesConfig cc = new ClassesConfig(classConfPath);
		JSONArray allSpecs = new JSONArray();
		for(String classpath : cc.classesKnown()) {
			JSONObject serviceSpec =new JSONObject();
			JSONArray operations = new JSONArray();

			serviceSpec.put("serviceName", classpath);
			serviceSpec.put("operations", operations);

			ClassesConfig.ClassInfo classInfo = cc.classInfo(classpath);
			if(classInfo.isAbstract()) {
				continue;
			}

			for(String methodName : classInfo.getAccessibleMethods()) {
				JSONObject operation = new JSONObject();
				JSONArray inputParams = new JSONArray();
				JSONArray outputParams = new JSONArray();
				JSONObject nonFunctionalProperties = new JSONObject();

				List<String> effects = new ArrayList<>();
				List<String> precond = new ArrayList<>();

				operations.add(operation);
				if(methodName.equals("$construct")) {
					operation.put("operationName", "__construct");
				} else {
					operation.put("operationName", methodName);
				}
				operation.put("inputParams", inputParams);
				operation.put("outputParams", outputParams);
				operation.put("nonFunctionalProperties", nonFunctionalProperties);

				ClassesConfig.MethodInfo methodInfo = classInfo.methodInfo(methodName);


				if(methodInfo.isStateMutating()) {
					effects.add("changesState(ServiceInstance)");
				}
				if(!methodInfo.isStatic()) {
					precond.add("needs(ServiceInstance)");
				}
				int parametersSkipped = 0;
				for (int i = 0, paramCount = methodInfo.paramCount(); i < paramCount; i++) {
					if(methodInfo.isParamFixed(i)) {
						parametersSkipped++;
						continue;
					}
					JSONObject param = new JSONObject();
					inputParams.add(param);
					int specParamIndex = i - parametersSkipped;
					param.put("name", ithInputName(specParamIndex));
					param.put("dataType", methodInfo.paramType(i));
					if(methodInfo.isParamStateMutating(i)) {
						effects.add("changesState(" + ithInputName(specParamIndex) + ")");
					}
				}
				if(methodInfo.hasReturnType()) {
					JSONObject output = new JSONObject();
					output.put("name", outputName());
					output.put("dataType", methodInfo.returnType());

					if(methodInfo.returnType().equals(classpath)) {
						/*
						 * service construct:
						 */
						effects.add("instanceOf(" + outputName() +", " + classpath + ")");
					}
					outputParams.add(output);
				} else  {
					/*
					 * Turn call by reference to call-by-value: 
					 */
					int stateMutatingParamIndex = methodInfo.indexOfNthStateMutatingParam(0);
					if(stateMutatingParamIndex >=0) {
						JSONObject output = new JSONObject();
						output.put("name", outputName());
						output.put("dataType", ((JSONObject)inputParams.get(stateMutatingParamIndex)).get("dataType"));
						outputParams.add(output);
					}
				}
				hardCodedEffects(classInfo, methodInfo, precond, effects);
				String precondition = precond.stream().collect(Collectors.joining(" & "));
				String effect = effects.stream().collect(Collectors.joining(" & "));
				operation.put("effect" , effect);
				operation.put("preCondition" , precondition);
			}
			allSpecs.add(serviceSpec);
			String serviceSpecJsonString = serviceSpec.toJSONString();
			FileUtil.writeStringToFile(new File(exportDir).getAbsolutePath() + "/" + classpath + ".json", serviceSpecJsonString);
		}
		FileUtil.writeStringToFile(new File(exportDir).getAbsolutePath() + "/all.json", allSpecs.toJSONString());
	}

	private String ithInputName(int i) {
		return Iterators.ordinal(i+1) + "_input";
	}

	private String outputName() {
		return "output";
	}

	private void hardCodedEffects(ClassesConfig.ClassInfo classInfo, ClassesConfig.MethodInfo methodInfo, List<String> precon, List<String> effect) {
		if(methodInfo.methodName().equals("train")) {
			effect.add("trained(ServiceInstance)");
		}
		if(methodInfo.methodName().equals("predict")) {
			effect.add("classifiedBy(" + outputName() + ", " + ithInputName(0) + ")");
		}
	}
}
