package ru.programpark.perf.dao;

import com.lmax.disruptor.EventFactory;
import org.jruby.Ruby;

import java.util.HashMap;

/**
 * Created by kozyr on 05.04.2016.
 */
public class TestObjectFactory implements EventFactory<TestObject> {

    private final String className;
    private static EventFactory<TestObject> rubyFactory;

    public TestObjectFactory(String className) {
        this.className = className.intern();
    }

    @Override
    public TestObject newInstance() {
        if (className == "Field") {
            return new FieldObject(Type.DATA);
        } else if (className == "BasicArray") {
            return new BasicArrayObject(Type.DATA, ArrayInfo.DATA_INFO);
        } else if (className == "ObjectArray") {
            return new BasicArrayObject(Type.DATA, ArrayInfo.OBJECT_DATA_INFO);
        } else if (className == "FixedArray") {
            return new FixedArrayObject(Type.DATA);
        } else if (className == "Hash") {
            return new HashObject(new HashMap<>(10));
        }  else if (className == "RubyObject") {
            return rubyFactory.newInstance();
        } else {
            throw new IllegalArgumentException("Unsupported data class: " + className);
        }
    }

    public static final String[] TYPES = new String[]{};

    static {
        rubyFactory = (EventFactory<TestObject>) Ruby.getGlobalRuntime().evalScriptlet(
                "java_import 'ru.programpark.perf.dao.TestObject'\n" +
                "java_import 'ru.programpark.perf.dao.Type'\n" +
                "java_import 'com.lmax.disruptor.EventFactory'\n" +
                "java_import 'java.io.Serializable'\n" +
                "class RubyObject \n" +
                "  include TestObject \n" +
                "  include Serializable \n" +
                        "def initialize\n" +
                        "  @f1=nil\n" +
                        "  @f2=nil\n" +
                        "  @f3=nil\n" +
                        "  @f4=nil\n" +
                        "  @f5=nil\n" +
                        "  @f6=nil\n" +
                        "  @f7=nil\n" +
                        "  @f8=nil\n" +
                        "  @f9=nil\n" +
                        "  @f10=nil\n" +
                        "  @type=Type::DATA\n" +
                        "end\n" +
                        "def set(idx, v)\n" +
                        "  instance_variable_set ('@'+idx).to_sym, v\n" +
                        "end\n" +
                        "def getObject(idx)\n" +
                        "  instance_variable_get ('@'+idx).to_sym\n" +
                        "end\n" +
                        "def getLong(idx)\n" +
                        "  instance_variable_get ('@'+idx).to_sym\n" +
                        "end\n" +
                        "def getDouble(idx)\n" +
                        "  instance_variable_get ('@'+idx).to_sym\n" +
                        "end\n" +
                        "def type\n" +
                        "  @type\n" +
                        "end\n" +
                        "def writeSelf(out)\n" +
                        "  out.writeUTF Marshal.dump self\n" +
                        "end\n" +
                "end\n" +
                "class Factory\n" +
                "  include EventFactory\n" +
                "  def newInstance\n" +
                "     RubyObject.new\n" +
                "  end\n" +
                "end\n" +
                "Factory.new\n" +
                "");
    }
}
