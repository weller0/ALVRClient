package com.polygraphene.alvr;

import java.util.concurrent.ConcurrentLinkedQueue;

public class NalQueue {
    private ConcurrentLinkedQueue<NAL> mUnusedList = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<NAL> mNalQueue = new ConcurrentLinkedQueue<>();
    private static final int SIZE = 100;
    private static final int DEFAULT_BUFFER_SIZE = 100 * 1000;

    NalQueue() {
        for (int i = 0; i < SIZE; i++) {
            NAL nal = new NAL();
            nal.buf = new byte[DEFAULT_BUFFER_SIZE];
            mUnusedList.add(nal);
        }
    }

    public NAL obtain(int length) {
        NAL nal = mUnusedList.poll();
        if (nal == null) {
            return null;
        }
        if (nal.buf.length < length) {
            nal.buf = new byte[length];
        }
        nal.length = length;
        return nal;
    }

    public void add(NAL nal) {
        mNalQueue.add(nal);
    }

    public NAL peek() {
        return mNalQueue.peek();
    }

    public void remove() {
        NAL nal = mNalQueue.remove();
        mUnusedList.add(nal);
    }

    public void clear() {
        mUnusedList.addAll(mNalQueue);
        mNalQueue.clear();
    }

    public int size() {
        return mNalQueue.size();
    }
}
