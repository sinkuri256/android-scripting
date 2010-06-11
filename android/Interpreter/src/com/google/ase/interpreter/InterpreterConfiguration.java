/*
 * Copyright (C) 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.ase.interpreter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;

import com.google.ase.interpreter.bsh.BshInterpreter;
import com.google.ase.interpreter.jruby.JRubyInterpreter;
import com.google.ase.interpreter.lua.LuaInterpreter;
import com.google.ase.interpreter.perl.PerlInterpreter;
import com.google.ase.interpreter.python.PythonInterpreter;
import com.google.ase.interpreter.rhino.RhinoInterpreter;
import com.google.ase.interpreter.sh.ShInterpreter;
import com.google.ase.interpreter.tcl.TclInterpreter;

/**
 * Manages and provides access to the set of available interpreters.
 * 
 * @author Damon Kohler (damonkohler@gmail.com)
 */
public class InterpreterConfiguration {

  private InterpreterConfiguration() {
    // Utility class.
  }

  private final static List<? extends Interpreter> mSupportedInterpreters =
      Arrays.asList(new BshInterpreter(), new JRubyInterpreter(), new LuaInterpreter(),
          new PerlInterpreter(), new PythonInterpreter(), new RhinoInterpreter(),
          new ShInterpreter(), new TclInterpreter());

  /**
   * Returns the list of all known interpreters.
   */
  public static List<? extends Interpreter> getSupportedInterpreters() {
    return mSupportedInterpreters;
  }

  /**
   * Returns the list of all installed interpreters.
   */
  public static List<Interpreter> getInstalledInterpreters(Context context) {
    List<Interpreter> interpreters = new ArrayList<Interpreter>();
    for (Interpreter i : mSupportedInterpreters) {
      if (i.isInstalled(context)) {
        interpreters.add(i);
      }
    }
    return interpreters;
  }

  /**
   * Returns the list of all not installed interpreters.
   */
  public static List<Interpreter> getNotInstalledInterpreters(Context context) {
    List<Interpreter> interpreters = new ArrayList<Interpreter>();
    for (Interpreter i : mSupportedInterpreters) {
      if (!i.isInstalled(context)) {
        interpreters.add(i);
      }
    }
    return interpreters;
  }

  /**
   * Returns the interpreter matching the provided name or null if no interpreter was found.
   */
  public static Interpreter getInterpreterByName(String interpreterName) {
    for (Interpreter i : mSupportedInterpreters) {
      if (i.getName().equals(interpreterName)) {
        return i;
      }
    }
    return null;
  }

  /**
   * Returns the correct interpreter for the provided script name based on the script's extension or
   * null if no interpreter was found.
   */
  public static Interpreter getInterpreterForScript(String scriptName) {
    int dotIndex = scriptName.lastIndexOf('.');
    if (dotIndex == -1) {
      return null;
    }
    String ext = scriptName.substring(dotIndex);
    for (Interpreter i : mSupportedInterpreters) {
      if (i.getExtension().equals(ext)) {
        return i;
      }
    }
    return null;
  }

  public static File getInterpreterRoot(Context context) {
    return context.getFilesDir().getParentFile();
  }

  public static File getInterpreterRoot(Context context, String interpreterName) {
    return new File(getInterpreterRoot(context), interpreterName);
  }
}