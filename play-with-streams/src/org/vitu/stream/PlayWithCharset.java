package org.vitu.stream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class PlayWithCharset {

	public static void main(String[] args) throws IOException {
		
		Path path = Path.of("files/lines-non-utf8.txt");
		
		Stream<String> lines = Files.lines(path, StandardCharsets.ISO_8859_1);
		
		System.out.println("Count = " + lines.count());

	}

}
