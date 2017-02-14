package com.meizu.g.a;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface c extends IInterface {

    public static abstract class a extends Binder implements c {

        private static class a implements c {
            private IBinder a;

            a(IBinder remote) {
                this.a = remote;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public void a() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.voiceassistant.support.IVoiceAssistantSpeakCallback");
                    this.a.transact(1, _data, _reply, 0);
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
                    _data.writeInterfaceToken("com.meizu.voiceassistant.support.IVoiceAssistantSpeakCallback");
                    this.a.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void c() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.voiceassistant.support.IVoiceAssistantSpeakCallback");
                    this.a.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static c a(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.meizu.voiceassistant.support.IVoiceAssistantSpeakCallback");
            if (iin == null || !(iin instanceof c)) {
                return new a(obj);
            }
            return (c) iin;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface("com.meizu.voiceassistant.support.IVoiceAssistantSpeakCallback");
                    a();
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface("com.meizu.voiceassistant.support.IVoiceAssistantSpeakCallback");
                    b();
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface("com.meizu.voiceassistant.support.IVoiceAssistantSpeakCallback");
                    c();
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.meizu.voiceassistant.support.IVoiceAssistantSpeakCallback");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void a() throws RemoteException;

    void b() throws RemoteException;

    void c() throws RemoteException;
}
