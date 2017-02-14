package com.meizu.cloud.app.block.structlayout;

public interface IStateableLayout {
    int getCurrentPosition();

    void pause();

    void resume();

    void start(int i);
}
