package io.vincent.compiler.sysdep;

import java.util.ArrayList;
import java.util.List;

public class AssemblerOptions {
    public boolean verbose = false;
    List<String> args = new ArrayList<>();

    public void addArg(String a) {
        args.add(a);
    }
}
