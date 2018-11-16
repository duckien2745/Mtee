package kienpd.com.mtee.data.model;

public class RatingTotal {

    private int mCount5Star = 0;

    private int mCount4Star = 0;

    private int mCount3Star = 0;

    private int mCount2Star = 0;

    private int mCount1Star = 0;

    public RatingTotal(int mCount5Star, int count4Star, int count3Star, int count2Star, int mCount1Star) {
        this.mCount5Star = mCount5Star;
        this.mCount4Star = mCount4Star;
        this.mCount3Star = mCount3Star;
        this.mCount2Star = mCount2Star;
        this.mCount1Star = mCount1Star;
    }

    public int getCount5Star() {
        return mCount5Star;
    }

    public void setCount5Star(int mCount5Star) {
        this.mCount5Star = mCount5Star;
    }

    public int getCount4Star() {
        return mCount4Star;
    }

    public void setCount4Star(int mCount4Star) {
        this.mCount4Star = mCount4Star;
    }

    public int getCount3Star() {
        return mCount3Star;
    }

    public void setCount3Star(int mCount3Star) {
        this.mCount3Star = mCount3Star;
    }

    public int getCount2Star() {
        return mCount2Star;
    }

    public void setCount2Star(int mCount2Star) {
        this.mCount2Star = mCount2Star;
    }

    public int getCount1Star() {
        return mCount1Star;
    }

    public void setCount1Star(int mCount1Star) {
        this.mCount1Star = mCount1Star;
    }
}
