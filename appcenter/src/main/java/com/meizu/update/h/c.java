package com.meizu.update.h;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

public class c {
    private static final char[] a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String a(String filename) {
        IOException e;
        Throwable th;
        InputStream is = null;
        try {
            InputStream is2 = new FileInputStream(filename);
            try {
                String a = a(is2);
                if (is2 != null) {
                    try {
                        is2.close();
                    } catch (IOException e2) {
                    }
                }
                is = is2;
                return a;
            } catch (IOException e3) {
                e = e3;
                is = is2;
                try {
                    e.printStackTrace();
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e4) {
                        }
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e5) {
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                is = is2;
                if (is != null) {
                    is.close();
                }
                throw th;
            }
        } catch (IOException e6) {
            e = e6;
            e.printStackTrace();
            if (is != null) {
                is.close();
            }
            return null;
        }
    }

    public static String a(InputStream is) {
        try {
            byte[] buffer = new byte[1024];
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            while (true) {
                int numRead = is.read(buffer);
                if (numRead <= 0) {
                    return a(md5.digest());
                }
                md5.update(buffer, 0, numRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String a(byte[] data) {
        int l = data.length;
        char[] out = new char[(l << 1)];
        int j = 0;
        for (int i = 0; i < l; i++) {
            int i2 = j + 1;
            out[j] = a[(data[i] & 240) >>> 4];
            j = i2 + 1;
            out[i2] = a[data[i] & 15];
        }
        return new String(out);
    }

    public static String a(String filename, int blockSize) {
        Exception e;
        Throwable th;
        InputStream inputStream = null;
        long fileLength = new File(filename).length();
        if (fileLength < ((long) (blockSize * 2))) {
            b.d("md5sumHeadTail file length is: " + fileLength);
            if (inputStream == null) {
                return null;
            }
            try {
                inputStream.close();
                return null;
            } catch (IOException e2) {
                return null;
            }
        }
        InputStream is = new FileInputStream(filename);
        int totalRead = 0;
        try {
            int numRead;
            byte[] buffer = new byte[1024];
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            while (totalRead < blockSize) {
                numRead = is.read(buffer, 0, blockSize - totalRead > buffer.length ? buffer.length : blockSize - totalRead);
                if (numRead <= 0) {
                    break;
                }
                md5.update(buffer, 0, numRead);
                totalRead += numRead;
            }
            is.close();
            inputStream = new FileInputStream(filename);
            long skipSize = fileLength - ((long) blockSize);
            while (skipSize > 0) {
                try {
                    long numSkip = inputStream.skip(skipSize);
                    if (numSkip > 0) {
                        skipSize -= numSkip;
                    } else {
                        b.d("skip file return: " + numSkip);
                    }
                } catch (Exception e3) {
                    e = e3;
                }
            }
            while (true) {
                numRead = inputStream.read(buffer);
                if (numRead <= 0) {
                    break;
                }
                md5.update(buffer, 0, numRead);
            }
            String str = a(md5.digest());
            if (inputStream == null) {
                return str;
            }
            try {
                inputStream.close();
                return str;
            } catch (IOException e4) {
                return str;
            }
        } catch (Exception e5) {
            e = e5;
            inputStream = is;
            try {
                e.printStackTrace();
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e6) {
                    }
                }
                return null;
            } catch (Throwable th2) {
                th = th2;
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e7) {
                    }
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            inputStream = is;
            if (inputStream != null) {
                inputStream.close();
            }
            throw th;
        }
    }
}
