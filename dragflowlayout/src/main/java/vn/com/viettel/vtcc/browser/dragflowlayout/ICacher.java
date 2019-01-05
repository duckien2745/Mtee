package vn.com.viettel.vtcc.browser.dragflowlayout;

/**
 * Created by duckien on 20/07/2017.
 */


public interface ICacher<T, P> {

    /** prepare for a number of T  if you need
     * @param  p the param */
    void prepare(P p);

    /**obtain T from cache . if not exist , create(p) will be called
     * @param p the param
     * @return the  T */
    T obtain(P p);

    /** clear the cache  */
    void clear();

    /** when cacher havn't , create by this mMethod
     *  @param p the param
     *  @return  the t */
    T create(P p);

    /** recycle it
     * @param t the t*/
    void recycle(T t);

}