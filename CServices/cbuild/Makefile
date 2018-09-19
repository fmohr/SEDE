JSOURCES=\
	gui/AppMainWindow.java \
	gui/ServiceViewerControl.java \
	gui/ImageDisplay.java \
	gnu/getopt/Getopt.java \
	gnu/getopt/LongOpt.java \
	app/ServiceCaller.java \
	measure/Measurement.java \
	measure/MeasurementData.java \
	measure/MsResult.java \
	output/mySysout.java \
	plugins/Plugin.java \
	plugins/Service.java \
	plugins/ServiceInstance.java \
	plugins/ServiceInstanceJava.java \
	plugins/ServiceInstancePlugin.java \
	plugins/ServiceInstancePython.java \
	plugins/ServiceJava.java \
	plugins/ServicePython.java \
	plugins/ServiceMetaInformation.java \
	plugins/Language.java \
	plugins/ServiceUtil.java\
	rmi/RMIServerDeploymentInterface.java \
	rmi/RMIClient.java \
	rmi/Data.java \
	rmi/RMIServerDataInterface.java \
	service/ServiceData.java \
	service/interfaces/AbstractNode.java \
	service/interfaces/Node.java \
	service/interfaces/MeasurementNode.java \
	service/interfaces/StartNode.java \
	service/interfaces/EndNode.java \
	service/interfaces/Edge.java \
	service/interfaces/Container.java \
	service/GraphErrorHandler.java \
	service/GraphDisplay.java \
	service/GraphImageDisplay.java \
	service/TreeValidator.java \
	service/ValidationResult.java \
	service/GraphParser.java \
	service/ResultRenderer.java \
	service/CsvReader.java \
	service/ServiceCallerData.java \
	service/CallParse.java \
	threads/ServiceCallerExecutor.java \
	threads/WebcamThread.java \
	image/Image.java \
	image/ImageMagickCaller.java \
	image/ImageManager.java \
	webcam/IWebcamImageProvider.java \
	webcam/WebcamFactory.java \
	webcam/WebcamImageProviderImpl.java \
	webcam/WebcamViewSize.java

JCLASSES=$(JSOURCES:.java=.class)
JPROPS=gnu/getopt/MessagesBundle.properties
JAVA=java -Xmx16G -jar
JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64/

CSOURCES=\
	imagemagick.c

COBJECTS=$(CSOURCES:.c=.o)
CC=gcc
CFLAGS=-O3 -Wall

VPATH=../csrc

all: clib_im libimagemagick.so

clib_im:
	javah -classpath ../build/classes/java/main -d ../csrc/include C2Data.C2ImageMagickCaller

libimagemagick.so: $(COBJECTS)
	cd ../csrc/build && \
	$(CC) $(CFLAGS) -shared -o $@ $+ -L/usr/lib/x86_64-linux-gnu -lMagickCore-6.Q16

%.o: %.c
	cd ../csrc/build && \
	$(CC) $(CFLAGS) -I$(JAVA_HOME)/include/linux -I$(JAVA_HOME)/include -I/usr/include/ImageMagick-6/ -I/usr/include/ImageMagick/ -I/usr/include/x86_64-linux-gnu/ImageMagick-6/ -I/usr/local/include/ImageMagick-6/ -fPIC -c -o $@ ../$<

