package org.ritsuka.youji.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Date: 04.10.11
 * Time: 17:37
 */
public class CommandParser {
    private final String command;
    private static final Pattern cmdPattern = Pattern.compile("^!(\\p{L}+)(\\s+)(.*)$", Pattern.UNICODE_CASE | Pattern.DOTALL);

    public class ParsedCommand {
        private final String name;
        private final String rawArg;
        private List<String> args;
        private final Map<String, String> kwargs;

        public ParsedCommand(String name, String rawArg) {
            this.name = name;
            this.rawArg = rawArg;
            this.args = null;
            this.kwargs = null;
            parseArgs();
        }

        private void parseArgs() {
            String[] splitted = rawArg.split("\\s+");
            args = new ArrayList<String>(Arrays.asList(splitted));
            for (String s : args)
                System.err.println(s);
        }

        public boolean is(String cmd){
            return name.equalsIgnoreCase(cmd);
        }

        public boolean in(List<String> cmds){
            for (String cmd:cmds) {
                if (is(cmd))
                    return true;
            }
            return false;
        }

        public String getName() {
            return name;
        }

        public String getRawArg() {
            return rawArg;
        }

        public List<String> getArgs() {
            return args;
        }

        public Map<String, String> getKwargs() {
            return kwargs;
        }
    }

    public CommandParser(String command) {
        this.command = command;
    }

    public ParsedCommand parse() {
        Matcher matcher = cmdPattern.matcher(command);

        if (!matcher.matches())
            return null;

        String name = matcher.group(1);
        String args = matcher.group(3);
        return new ParsedCommand(name, args);
    }
}
