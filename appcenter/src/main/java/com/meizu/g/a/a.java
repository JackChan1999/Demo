package com.meizu.g.a;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface a extends IInterface {

    public static abstract class a extends Binder implements a {

        private static class a implements a {
            private IBinder a;

            a(IBinder remote) {
                this.a = remote;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public void a(Intent rlt) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.voiceassistant.support.IVoiceAssistantCallback");
                    if (rlt != null) {
                        _data.writeInt(1);
                        rlt.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.a.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void b(Intent rlt) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.voiceassistant.support.IVoiceAssistantCallback");
                    if (rlt != null) {
                        _data.writeInt(1);
                        rlt.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.a.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public a() {
            attachInterface(this, "com.meizu.voiceassistant.support.IVoiceAssistantCallback");
        }

        public static a a(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.meizu.voiceassistant.support.IVoiceAssistantCallback");
            if (iin == null || !(iin instanceof a)) {
                return new a(obj);
            }
            return (a) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            Intent _arg0;
            switch (code) {
                case 1:
                    data.enforceInterface("com.meizu.voiceassistant.support.IVoiceAssistantCallback");
                    if (data.readInt() != 0) {
                        _arg0 = (Intent) Intent.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    a(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface("com.meizu.voiceassistant.support.IVoiceAssistantCallback");
                    if (data.readInt() != 0) {
                        _arg0 = (Intent) Intent.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    b(_arg0);
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.meizu.voiceassistant.support.IVoiceAssistantCallback");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void a(Intent intent) throws RemoteException;

    void b(Intent intent) throws RemoteException;
}
