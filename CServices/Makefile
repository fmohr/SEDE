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

all: clean server

server: classes clib_im libimagemagick.so
	# mkdir -p ../bin
	# cd classes && \
	# jar -cvmf ../manifest ../../bin/service_caller.jar $(JCLASSES)  gui/*.class -C ../../src $(JPROPS)
	# cp -f bridj-0.6.2.jar ../bin/
	# cp -f slf4j-api-1.7.2.jar ../bin/
	# cp -f webcam-capture-0.3.10.jar ../bin/

classes:
	# mkdir -p classes
	# javac -d classes -classpath ../src:bridj-0.6.2.jar:slf4j-api-1.7.2.jar:webcam-capture-0.3.10.jar ../src/app/ServiceCaller.java

clib_im:
	# mkdir -p ../bin ../csrc ../csrc/build ../csrc/include
	javah -classpath ../out/production/CServices -d ../csrc/include C2Data.C2ImageMagickCaller

libimagemagick.so: $(COBJECTS)
	cd ../csrc/build && \
	$(CC) $(CFLAGS) -shared -o $@ $+ -L/usr/lib/x86_64-linux-gnu -lMagickCore-6.Q16

%.o: %.c
	cd ../csrc/build && \
	$(CC) $(CFLAGS) -I$(JAVA_HOME)/include/linux -I$(JAVA_HOME)/include -I/usr/include/ImageMagick-6/ -I/usr/include/ImageMagick/ -I/usr/include/x86_64-linux-gnu/ImageMagick-6/ -I/usr/local/include/ImageMagick-6/ -fPIC -c -o $@ ../$<

clean:
	rm -rf classes *.log ../csrc/build/*

cleanall: clean
	rm -rf ../bin/*

rungui:
	@$(JAVA) ../bin/service_caller.jar -m

services:
	@$(JAVA) ../bin/service_caller.jar -a

help:
	@$(JAVA) ../bin/service_caller.jar -h
