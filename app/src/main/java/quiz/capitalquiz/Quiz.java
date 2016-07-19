package quiz.capitalquiz;

/**
 * Created by Юлия on 14.06.2016.
 */
public class Quiz {
    private String mCapital;
    private String mCountry;
    private boolean mCorrectly;

    public String getCapital() {
        return mCapital;
    }

    public void setCapital(String capital) {
        mCapital = capital;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public boolean isCorrectly() {
        return mCorrectly;
    }

    public void setCorrectly(boolean correctly) {
        mCorrectly = correctly;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "mCapital='" + mCapital + '\'' +
                ", mCountry='" + mCountry + '\'' +
                ", mCorrectly=" + mCorrectly +
                '}';
    }
}
