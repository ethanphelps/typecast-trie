clean:
	./gradlew clean

build: clean 
	./gradlew build

run: build
	java -cp build/classes/java/main:build/resources/main lol.typefast.trie.App

rerun: 
	java -cp build/classes/java/main:build/resources/main lol.typefast.trie.App