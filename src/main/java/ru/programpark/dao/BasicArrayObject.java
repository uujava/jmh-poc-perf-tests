package BenchmarksProject.ringbuffer;

/**
 * Created by user on 4/2/2016.
 */
public class BasicArrayObject implements TestObject {

    private final double[] numeric;
    private final Object[] objects;
    private final ArrayInfo info;

    public BasicArrayObject(Type type, ArrayInfo info) {
        this.info = info;
        numeric = new double[info.getMaxDouble()];
        objects = new Object[info.getMaxObject()];
        set("type", type);
    }

    @Override
    public void set(String idx, long v) {
        numeric[idx(idx)] = v;
    }

    @Override
    public void set(String idx, int v) {
        numeric[idx(idx)] = v;
    }

    @Override
    public void set(String idx, double v) {
        numeric[idx(idx)] = v;
    }

    @Override
    public void set(String idx, float v) {
        numeric[idx(idx)] = v;
    }

    @Override
    public void set(String idx, Object v) {
        int _idx = idx(idx);
        objects[_idx - numeric.length] = v;
    }

    @Override
    public Object getObject(String idx) {
        int _idx = idx(idx);
        if (_idx < numeric.length) {
            return numeric[_idx];
        } else {
            return objects[_idx - numeric.length];
        }
    }

    @Override
    public long getLong(String idx) {
        return (long) numeric[idx(idx)];
    }

    protected int idx(String idx) {
        return info.idx(idx);
    }

    @Override
    public double getDouble(String idx) {
        return numeric[idx(idx)];
    }

    @Override
    public Type type() {
        return (Type) getObject("type");
    }

}
