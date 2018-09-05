package de.upb.sede.webinterfaces.server;

import java.util.*;
import java.util.stream.Collectors;
import static de.upb.sede.webinterfaces.server.Command.Match;

public class CommandTree {

	private Node root;

	public CommandTree(Node root) {
		this.root = Objects.requireNonNull(root);
	}

	public static Node node(Command command, Node... children){
		Objects.requireNonNull(command);
		Objects.requireNonNull(children);
		for (Node n : children) {
			Objects.requireNonNull(n);
		}
		return new Node(command, new ArrayList<>(Arrays.asList(children)));
	}

	public static String lastMatch(List<Match> matches) {
		if(matches.size()>0) {
			return matches.get(matches.size() - 1).getMatch();
		} else {
			throw new CommandFormatMismatch();
		}
	}

	public static String[] rest(List<Match> matches) {
		if(matches.size()>0) {
			return matches.get(matches.size() - 1).getRest();
		} else {
			return new String[0];
		}
	}

	public Node getRoot(){
		return root;
	}

	public static List<String> toStrList(List<Match> matches){
		return toStrList(matches, false);
	}

	public static List<String> toStrList(List<Match> matches, boolean rest){
		List<String> stringMatches = matches.stream().map(Match::getMatch).collect(Collectors.toList());
		if(rest && matches.size()>0) {
			Arrays.stream(matches.get(matches.size() - 1).getRest()).forEachOrdered(stringMatches::add);
		}
		return stringMatches;
	}

	public void addOptionToRoot(CommandTree newOption) {
		root.children.add(newOption.root);
	}

	public boolean matches(String[] inputs) {
		return nodeMatches(root, inputs);
	}

	private boolean nodeMatches(Node node, String[] inputs) {
		if(!node.command.matches(inputs)) {
			return false;
		} else if(node.children.isEmpty()) {
			return true;
		} else {
			inputs = node.command.match(inputs).getRest();
			for(Node child : node.children) {
				if(nodeMatches(child, inputs)) {
					return true;
				}
			}
		}
		return false;
	}


	public String execute(String[] inputs) {
		if(matches(inputs)) {
			List<Match> matches = new ArrayList<>();
			return execute(root, inputs, matches);
		} else {
			throw new CommandFormatMismatch();
		}
	}

	private String execute(Node node, String[] rest, List<Match> matches) {
		if(node.children.isEmpty()){
			return node.command.execute(matches);
		} else {
			for(Node n : node.children) {
				if(nodeMatches(n, rest)) {
					Match m = n.command.match(rest);
					matches.add(m);
					return execute(n, m.getRest(), matches);
				}
			}
			throw new CommandFormatMismatch();
		}
	}


	public List<Match> createMatches(String[] inputs) {
		if(!matches(inputs)){
			throw new RuntimeException();
		}
		List<Match> matches = new ArrayList<>();
		fillNodeMatches(root, inputs, matches);
		return matches;
	}

	private void fillNodeMatches(Node node, String[] inputs, List<Match> matches) {
		Match nodeMatch = node.command.createMatch(inputs);
		for(Node child : node.children) {
			if(nodeMatches(child, nodeMatch.getRest())) {
				fillNodeMatches(child, nodeMatch.getRest(), matches);
				break;
			}
		}
		matches.add(0, nodeMatch);
	}

	public static class Node {
		List<Node> children;
		Command command;
		private Node(Command command, List<Node> children) {
			this.command =command;
			this.children = children;
		}

		public List<Node> getChildren() {
			return children;
		}

		public Command getCommand() {
			return command;
		}
	}

}
