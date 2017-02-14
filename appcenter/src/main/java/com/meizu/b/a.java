package com.meizu.b;

import android.os.Binder;
import android.os.Bundle;
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

            public boolean a(String targetPackage, long compaignId, long taskId, Bundle extras) throws RemoteException {
                boolean _result = true;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.compaign.ITaskResponse");
                    _data.writeString(targetPackage);
                    _data.writeLong(compaignId);
                    _data.writeLong(taskId);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.a.transact(1, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean a(String targetPackage, String taskType, String taskData) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken("com.meizu.compaign.ITaskResponse");
                    _data.writeString(targetPackage);
                    _data.writeString(taskType);
                    _data.writeString(taskData);
                    this.a.transact(2, _data, _reply, 0);
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
        }

        public static a a(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface("com.meizu.compaign.ITaskResponse");
            if (iin == null || !(iin instanceof a)) {
                return new a(obj);
            }
            return (a) iin;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int i = 0;
            boolean _result;
            switch (code) {
                case 1:
                    Bundle _arg3;
                    int i2;
                    data.enforceInterface("com.meizu.compaign.ITaskResponse");
                    String _arg0 = data.readString();
                    long _arg1 = data.readLong();
                    long _arg2 = data.readLong();
                    if (data.readInt() != 0) {
                        _arg3 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg3 = null;
                    }
                    _result = a(_arg0, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    if (_result) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    reply.writeInt(i2);
                    return true;
                case 2:
                    data.enforceInterface("com.meizu.compaign.ITaskResponse");
                    _result = a(data.readString(), data.readString(), data.readString());
                    reply.writeNoException();
                    if (_result) {
                        i = 1;
                    }
                    reply.writeInt(i);
                    return true;
                case 1598968902:
                    reply.writeString("com.meizu.compaign.ITaskResponse");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    boolean a(String str, long j, long j2, Bundle bundle) throws RemoteException;

    boolean a(String str, String str2, String str3) throws RemoteException;
}
