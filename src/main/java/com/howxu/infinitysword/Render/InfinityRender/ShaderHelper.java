package com.howxu.infinitysword.Render.InfinityRender;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.ARBShaderObjects;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class ShaderHelper {
    public static int cosmicShader = 0;

    public ShaderHelper() {
    }

    public static void initShaders() {
        if (useShaders()) {
            cosmicShader = createProgram("cosmic.vert", "cosmic.frag");//对应使用assets的两个shader文件
        }
    }

    public static void useShader(int shader, ShaderCallback callback) {
        if (useShaders()) {
            ARBShaderObjects.glUseProgramObjectARB(shader);
            if (shader != 0) {
                int time = ARBShaderObjects.glGetUniformLocationARB(shader, "time");
                Minecraft mc = Minecraft.getMinecraft();
                if (mc.thePlayer != null && mc.thePlayer.worldObj != null) {
                    ARBShaderObjects.glUniform1iARB(time, (int) (mc.thePlayer.worldObj.getWorldTime() % 2147483647L));
                }

                if (callback != null) {
                    callback.call(shader);
                }
            }

        }
    }

    public static void useShader(int shader) {
        useShader(shader, null);
    }

    public static void releaseShader() {
        useShader(0);
    }

    public static boolean useShaders() {
        return OpenGlHelper.shadersSupported;
    }

    private static int createProgram(String vert, String frag) {
        int vertId = 0;
        int fragId = 0;
        int program;
        if (vert != null) {
            vertId = createShader("/assets/infinity/shader/" + vert, 35633);//这里定义了文件路径
        }

        if (frag != null) {
            fragId = createShader("/assets/infinity/shader/" + frag, 35632);
        }

        program = ARBShaderObjects.glCreateProgramObjectARB();
        if (program == 0) {
            return 0;
        } else {
            if (vert != null) {
                ARBShaderObjects.glAttachObjectARB(program, vertId);
            }

            if (frag != null) {
                ARBShaderObjects.glAttachObjectARB(program, fragId);
            }

            ARBShaderObjects.glLinkProgramARB(program);
            if (ARBShaderObjects.glGetObjectParameteriARB(program, 35714) == 0) {
                return 0;
            } else {
                ARBShaderObjects.glValidateProgramARB(program);
                if (ARBShaderObjects.glGetObjectParameteriARB(program, 35715) == 0) {
                    return 0;
                } else {
                    return program;
                }
            }
        }
    }

    private static int createShader(String filename, int shaderType) {
        int shader = 0;

        try {
            shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
            if (shader == 0) {
                return 0;
            } else {
                ARBShaderObjects.glShaderSourceARB(shader, readFileAsString(filename));
                ARBShaderObjects.glCompileShaderARB(shader);
                if (ARBShaderObjects.glGetObjectParameteriARB(shader, 35713) == 0) {
                    throw new RuntimeException("Error creating shader \"" + filename + "\": " + getLogInfo(shader));
                } else {
                    return shader;
                }
            }
        } catch (Exception var4) {
            ARBShaderObjects.glDeleteObjectARB(shader);
            var4.printStackTrace();
            return -1;
        }
    }

    private static String getLogInfo(int obj) {
        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, 35716));
    }

    private static String readFileAsString(String filename) throws Exception {
        StringBuilder source = new StringBuilder();
        InputStream in = ShaderHelper.class.getResourceAsStream(filename);
        Exception exception = null;
        if (in == null) {
            return "";
        } else {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                Exception innerExc = null;

                try {
                    String line;
                    try {
                        while ((line = reader.readLine()) != null) {
                            source.append(line).append('\n');
                        }
                    } catch (Exception var31) {
                        exception = var31;
                    }
                } finally {
                    try {
                        reader.close();
                    } catch (Exception var30) {
                        if (innerExc == null) {
                            innerExc = var30;
                        } else {
                            var30.printStackTrace();
                        }
                    }

                }

                if (innerExc != null) {
                    throw innerExc;
                }
            } catch (Exception var33) {
                exception = var33;
            } finally {
                try {
                    in.close();
                } catch (Exception var29) {
                    if (exception == null) {
                        exception = var29;
                    } else {
                        var29.printStackTrace();
                    }
                }

                if (exception != null) {
                    throw exception;
                }

            }

            return source.toString();
        }
    }
}
