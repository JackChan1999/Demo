package com.meizu.cloud.app.downlad;

public class f {

    public interface m {
    }

    public interface b extends m {
        void onDownloadProgress(e eVar);

        void onDownloadStateChanged(e eVar);
    }

    public interface d extends m {
        void onFetchStateChange(e eVar);
    }

    public interface e extends m {
        void onInstallStateChange(e eVar);
    }

    public interface g extends m {
        void b(e eVar);
    }

    public interface i extends m {
        void a(e eVar);
    }

    public interface l {
    }

    public enum a implements l {
        NOT_INSTALL,
        INSTALLED
    }

    public enum c implements l {
        TASK_CREATED,
        TASK_WAITING,
        TASK_STARTED,
        TASK_PAUSED,
        TASK_ERROR,
        TASK_COMPLETED,
        TASK_REMOVED,
        TASK_RESUME
    }

    public enum f implements l {
        INSTALL_START,
        INSTALL_SUCCESS,
        INSTALL_FAILURE,
        DELETE_START,
        DELETE_SUCCESS,
        DELETE_FAILURE
    }

    public enum h implements l {
        PATCHING,
        PATCHED_SUCCESS,
        PATCHED_FAILURE
    }

    public enum j implements l {
        PAYING,
        SUCCESS,
        FAILURE,
        CANCEL
    }

    public static abstract class k implements b, d, e, g, i {
        public void onDownloadStateChanged(e wrapper) {
        }

        public void onDownloadProgress(e wrapper) {
        }

        public void onFetchStateChange(e wrapper) {
        }

        public void onInstallStateChange(e wrapper) {
        }

        public void b(e wrapper) {
        }

        public void a(e wrapper) {
        }
    }

    public enum n implements l {
        FETCHING,
        SUCCESS,
        FAILURE,
        CANCEL
    }

    public static boolean a(l anEnum) {
        return anEnum == a.INSTALLED || anEnum == a.NOT_INSTALL || anEnum == n.FAILURE || anEnum == n.CANCEL || anEnum == c.TASK_ERROR || anEnum == c.TASK_REMOVED || anEnum == f.DELETE_FAILURE || anEnum == f.DELETE_SUCCESS || anEnum == f.INSTALL_FAILURE || anEnum == f.INSTALL_SUCCESS || anEnum == j.CANCEL || anEnum == j.FAILURE || anEnum == h.PATCHED_FAILURE;
    }

    public static boolean b(l anEnum) {
        return anEnum == a.NOT_INSTALL || anEnum == n.FAILURE || anEnum == n.CANCEL || anEnum == c.TASK_ERROR || anEnum == c.TASK_REMOVED || anEnum == f.DELETE_FAILURE || anEnum == f.INSTALL_FAILURE || anEnum == j.CANCEL || anEnum == j.FAILURE || anEnum == h.PATCHED_FAILURE;
    }

    public static boolean c(l anEnum) {
        return anEnum == n.FAILURE || anEnum == c.TASK_ERROR || anEnum == f.INSTALL_FAILURE || anEnum == f.DELETE_FAILURE || anEnum == h.PATCHED_FAILURE || anEnum == j.FAILURE;
    }

    public static boolean d(l anEnum) {
        return (anEnum == c.TASK_COMPLETED || anEnum == h.PATCHING || anEnum == f.INSTALL_START || anEnum == f.DELETE_START) ? false : true;
    }
}
