package de.upb.sede.webinterfaces.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class Command {

	public static final Command rest() {
		return new ConsumeRest();
	}
	public static final Command nothing() {
		return new ConsumeNothing();
	}
	private Function<List<Match>, String> exeFunction = list -> {
		throw new RuntimeException("Not implemented");
	};

	public abstract boolean matches(String[] inputs);

	public Match match(String[] inputs) {
		if(!matches(inputs)) {
			throw new RuntimeException("BUG: Doesn't match input: " + Arrays.toString(inputs));
		}
		return createMatch(inputs);
	}

	protected abstract Match createMatch(String[] inputs);

	public String execute(List<Match> matches) {
		return exeFunction.apply(matches);
	}

	public Command addExe(Function<List<Match>, String> function) {
		exeFunction = Objects.requireNonNull(function);
		return this;
	}


	public static class Strings extends Command{
		private final List<String> matches;
		private final boolean matchCase;

		public Strings(String... matches) {
			this(true, matches);
		}

		public Strings(List<String> matches) {
			this(matches, true);
		}

		public Strings(boolean caseSensitive, String... matches) {
			this(Arrays.asList(Objects.requireNonNull(matches)), caseSensitive);
		}

		public Strings(List<String> matches, boolean caseSensitive) {
			this.matches = new ArrayList<>(Objects.requireNonNull(matches));
			this.matchCase = caseSensitive;
			if(matches.isEmpty()) {
				throw new RuntimeException("Provide at least one match.");
			}

		}

		public List<String> getMatches() {
			return matches;
		}

		public boolean isMatchCase() {
			return matchCase;
		}

		@Override
		public boolean matches(String[] inputs) {
			if(inputs.length == 0) {
				return false;
			}
			String input = inputs[0];
			for(String s : matches) {
				if(input.equals(s) || (!matchCase && input.equalsIgnoreCase(s))) {
					return true;
				}
			}
			return false;
		}

		@Override
		public Match createMatch(String[] inputs) {
			for(String s : matches) {
				Match m;
				String input = inputs[0];
				if(input.equals(s) || (!matchCase && input.equalsIgnoreCase(s))) {
					return Match.removeFirst(input, inputs);
				}
			}
			throw new RuntimeException("Cannot be reached.");
		}
	}

	public static class Token extends Command{

		public Token() {
		}


		@Override
		public boolean matches(String[] inputs) {
			if(inputs.length == 0) {
				return false;
			} else {
				return true;
			}
		}

		@Override
		public Match createMatch(String[] inputs) {
			String input = inputs[0];
			return Match.removeFirst(input, inputs);
		}
	}

	public static class File extends Command {
		private final String path;
		private final boolean directories, files;

		public File(String path, boolean directories, boolean files) {
			if(!directories && !files) {
				throw new RuntimeException("Either accept directories or files.");
			}
			this.path = Objects.requireNonNull(path);
			this.directories = directories;
			this.files = files;
		}
		public File() {
			this(".", true, true);
		}

		public String getPath() {
			return path;
		}

		public boolean isDirectories() {
			return directories;
		}

		public boolean isFiles() {
			return files;
		}

		@Override
		public boolean matches(String[] inputs) {
			return inputs.length > 0;
		}

		@Override
		protected Match createMatch(String[] inputs) {
			return Match.removeFirst(inputs[0], inputs);
		}
	}

	public static class ConsumeNothing extends Command {

		@Override
		public boolean matches(String[] inputs) {
			return true;
		}

		@Override
		protected Match createMatch(String[] inputs) {
			return new Match("", inputs);
		}
	}

	public static class ConsumeRest extends  Command {

		@Override
		public boolean matches(String[] inputs) {
			return true;
		}

		@Override
		protected Match createMatch(String[] inputs) {
			return new Match(Arrays.stream(inputs).collect(Collectors.joining("$")), new String[0]);
		}
	}

	public static class Match {
		private final String match;
		private final String[] rest;
		Match(String match, String[] rest) {
			this.rest = rest;
			this.match = match;
		}
		public String getMatch() {
			return match;
		}

		public String[] getRest() {
			return rest;
		}
		private static Match removeFirst(String match, String[] inputs) {
			String[] rest = new String[inputs.length-1];
			for (int i = 1; i < inputs.length; i++) {
				rest[i-1] = inputs[i];
			}
			Match m = new Match(match, rest);
			return m;
		}
	}
}
