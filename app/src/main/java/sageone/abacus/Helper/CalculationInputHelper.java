package sageone.abacus.Helper;

import android.app.Activity;
import java.util.HashMap;

import sageone.abacus.Exceptions.ValidationException;
import sageone.abacus.Models.InputData;

/**
 * Created by otomaske on 10.02.2016.
 */
public class CalculationInputHelper
{
    private Activity a;
    public InputData data;

    public static final String WAGE_TYPE_GROSS   = "Bruttolohn";
    public static final String WAGE_TYPE_NET     = "Nettolohn";
    public static final String WAGE_PERIOD_YEAR  = "y";
    public static final String WAGE_PERIOD_MONTH = "m";


    public CalculationInputHelper(Activity a, InputData data)
    {
        this.a = a;
        this.data = data;
    }


    /**
     * Validates the inputs.
     *
     * @throws ValidationException
     * @return boolean
     */
    public boolean validate() throws ValidationException
    {
        return true;
    }


    /**
     * Checks data if bigger then null and 0.
     *
     * @param data
     * @return
     */
    private boolean _nullOrEmpty(Double data)
    {
        return (null == data || 0.0 == data);
    }




    /**
     * States translator.
     *
     * @param state
     * @return
     */
    private int translateState(String state)
    {
        HashMap<String, Integer> states = new HashMap<String, Integer>();
        states.put("Baden-Württemberg", 1);
        states.put("Bayern", 2);
        states.put("Berlin-West", 3);
        states.put("Brandenburg", 4);
        states.put("Bremen", 5);
        states.put("Hamburg", 6);
        states.put("Hessen", 7);
        states.put("Mecklenburg-Vorpommern", 8);
        states.put("Niedersachsen", 9);
        states.put("Nordrhein-Westfalen", 10);
        states.put("Rheinland-Pfalz", 11);
        states.put("Saarland", 12);
        states.put("Sachsen", 13);
        states.put("Sachsen-Anhalt", 14);
        states.put("Schleswig-Holstein", 15);
        states.put("Thüringen", 16);
        states.put("Berlin-West", 30);

        return states.get(state);
    }

}
