package org.fxapps.cpp.lib;

import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;

@Platform(include="NativeLibrary.h")
@Namespace("NativeLibrary")
public class NativeLibrary {
    public static class NativeClass extends Pointer {
        static { Loader.load(); }
        public NativeClass() { allocate(); }
        private native void allocate();

        // to call the getter and setter functions 
        public native @StdString String get_property(); public native void set_property(String property);

        // to access the member variable directly
        public native @StdString String property();     public native void property(String property);
    }
}