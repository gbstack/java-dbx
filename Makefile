DIR=net/sf/oereader
JAVA=java
JAVAFILES := $(wildcard $(DIR)/*.java)
JAVACLASSFILES := $(patsubst %.java, %.class, $(JAVAFILES))
JAVAC=javac
JAVADOC=javadoc
JAVAC_FLAGS=
JARFILE=OEReader.jar
JAVADOC_OUTPUT=javadoc

SFTP=sftp
SFTP_BATCH_FILE=sftp_batch
SFTP_ADDRESS=wolf1oo@web.sf.net

all: jar

jar: build
	@echo -n "Creating jar file..."
	@jar cf $(JARFILE) $(DIR)/*.class
	@echo " Done"

build: $(JAVACLASSFILES)

%.class: %.java
	@echo -n "Compiling $<..."
	@$(JAVAC) $(JAVAC_FLAGS) $<
	@echo " Done"

clean: clean_doc
	rm -f $(DIR)/*.class

doc:
	$(JAVADOC) -d $(JAVADOC_OUTPUT) $(JAVAFILES)

clean_doc:
	rm -rf $(JAVADOC_OUTPUT)

test: build_test
	@echo "Running Test... Output:"
	@$(JAVA) -cp "$(JARFILE):test" Test
	@echo
	@echo "Done"

build_test: test/Test.class
	@echo -n "Building Test.java..."
	@$(JAVAC) -cp "$(JARFILE)" $(JAVAC_FLAGS) test/Test.java
	@echo "Done"

upload:
	$(SFTP) -o "batchmode no" -b $(SFTP_BATCH_FILE) $(SFTP_ADDRESS)
