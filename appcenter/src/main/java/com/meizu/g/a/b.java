package com.meizu.g.a;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface b extends IInterface {

    public static abstract class a extends Binder implements b {

        private static class a implements b {
            private IBinder a;

            a(IBinder remote) {
                this.a = remote;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public void a(String packageName, int focuses) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    _data.writeString(packageName);
                    _data.writeInt(focuses);
                    this.a.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void a(String packageName, a callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    _data.writeString(packageName);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.a.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void a(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    _data.writeString(packageName);
                    this.a.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void a(String packageName, IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    _data.writeString(packageName);
                    _data.writeStrongBinder(token);
                    this.a.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void a(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    _data.writeStrongBinder(token);
                    this.a.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean a() throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    this.a.transact(6, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void a(String packageName, String[] localScene) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    _data.writeString(packageName);
                    _data.writeStringArray(localScene);
                    this.a.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void a(String packageName, String text) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    _data.writeString(packageName);
                    _data.writeString(text);
                    this.a.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void a(String packageName, long timeNode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    _data.writeString(packageName);
                    _data.writeLong(timeNode);
                    this.a.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void b() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    this.a.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void a(String packageName, c callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    _data.writeString(packageName);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.a.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void b(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    _data.writeString(packageName);
                    this.a.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void b(String packageName, String text) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    _data.writeString(packageName);
                    _data.writeString(text);
                    this.a.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean c() throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    this.a.transact(14, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void d() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    this.a.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void c(String hintText, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    _data.writeString(hintText);
                    _data.writeString(packageName);
                    this.a.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static b b(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.meizu.voiceassistant.support.IVoiceAssistantService");
            if (iin == null || !(iin instanceof b)) {
                return new a(obj);
            }
            return (b) iin;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int i = 0;
            boolean _result;
            switch (code) {
                case 1:
                    data.enforceInterface("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    a(data.readString(), data.readInt());
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    a(data.readString(), com.meizu.g.a.a.a.a(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    a(data.readString());
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    a(data.readString(), data.readStrongBinder());
                    reply.writeNoException();
                    return true;
                case 5:
                    data.enforceInterface("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    a(data.readStrongBinder());
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    _result = a();
                    reply.writeNoException();
                    if (_result) {
                        i = 1;
                    }
                    reply.writeInt(i);
                    return true;
                case 7:
                    data.enforceInterface("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    a(data.readString(), data.createStringArray());
                    reply.writeNoException();
                    return true;
                case 8:
                    data.enforceInterface("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    a(data.readString(), data.readString());
                    reply.writeNoException();
                    return true;
                case 9:
                    data.enforceInterface("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    a(data.readString(), data.readLong());
                    reply.writeNoException();
                    return true;
                case 10:
                    data.enforceInterface("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    b();
                    reply.writeNoException();
                    return true;
                case 11:
                    data.enforceInterface("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    a(data.readString(), com.meizu.g.a.c.a.a(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 12:
                    data.enforceInterface("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    b(data.readString());
                    reply.writeNoException();
                    return true;
                case 13:
                    data.enforceInterface("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    b(data.readString(), data.readString());
                    reply.writeNoException();
                    return true;
                case 14:
                    data.enforceInterface("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    _result = c();
                    reply.writeNoException();
                    if (_result) {
                        i = 1;
                    }
                    reply.writeInt(i);
                    return true;
                case 15:
                    data.enforceInterface("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    d();
                    reply.writeNoException();
                    return true;
                case 16:
                    data.enforceInterface("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    c(data.readString(), data.readString());
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.meizu.voiceassistant.support.IVoiceAssistantService");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void a(IBinder iBinder) throws RemoteException;

    void a(String str) throws RemoteException;

    void a(String str, int i) throws RemoteException;

    void a(String str, long j) throws RemoteException;

    void a(String str, IBinder iBinder) throws RemoteException;

    void a(String str, a aVar) throws RemoteException;

    void a(String str, c cVar) throws RemoteException;

    void a(String str, String str2) throws RemoteException;

    void a(String str, String[] strArr) throws RemoteException;

    boolean a() throws RemoteException;

    void b() throws RemoteException;

    void b(String str) throws RemoteException;

    void b(String str, String str2) throws RemoteException;

    void c(String str, String str2) throws RemoteException;

    boolean c() throws RemoteException;

    void d() throws RemoteException;
}
