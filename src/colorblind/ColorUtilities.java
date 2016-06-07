package colorblind;

import colorblind.generators.util.Matrix;
import colorblind.generators.util.Vector;

public class ColorUtilities {

    private static Matrix rgb2lms = new Matrix(0.178860079f, 0.43997117f,
            0.035965767f, 0.0338043137f, 0.275152424f, 0.036206346f,
            0.000310889112f, 0.00191660736f, 0.0152808899f);

    private static Matrix lms2rgb = new Matrix(8.00533289f, -12.8819614f,
            11.6806557f, -0.978222203f, 5.26946627f, -10.1830200f,
            -0.0401745554f, -0.398840403f, 66.4807787f);

    /**
     * Apply Gamma Correction
     * 
     * @param s
     *            = value [0, 1]
     * @return
     */
    public static float applyGammaCorrection(float s, float gamma) {
        return (float) Math.pow(s, 1 / gamma);
    }

    /**
     * Remove Gamma Correction
     * 
     * @param s
     *            = value [0, 1]
     * @return
     */
    public static float removeGammaCorrection(float s, float gamma) {
        return (float) Math.pow(s, gamma);
    }

    /**
     * Apply Standard Gamma Correction
     * 
     * @param s
     *            = value [0, 1]
     * @return
     */
    public static float applyGammaCorrectionStandardRGB(float s) {
        if (s <= 0.0031308) {
            return 12.92f * s;
        } else {
            return (float) (1.055 * Math.pow(s, 0.41666) - 0.055);
        }
    }

    /**
     * Remove Standard Gamma Correction
     * 
     * @param s
     *            = value [0, 1]
     * @return
     */
    public static float removeGammaCorrectionStandardRGB(float s) {
        if (s <= 0.04045f) {
            return s / 12.92f;
        } else {
            return (float) Math.pow((s + 0.055) / 1.055, 2.4);
        }
    }

    public static Vector convertRGB2LMS(int color) {
        int sr = (color & 0x00FF0000) >> 16;
        int sg = (color & 0x0000FF00) >> 8;
        int sb = (color & 0x000000FF);

        // Remove gamma correction.
        float pow_r = removeGammaCorrectionStandardRGB(sr);
        float pow_g = removeGammaCorrectionStandardRGB(sg);
        float pow_b = removeGammaCorrectionStandardRGB(sb);

        Vector c = new Vector(pow_r, pow_g, pow_b);
        Vector lms = rgb2lms.rightMult(c);

        return lms;
    }

    public static Vector convertRGB2LMS(Vector color) {
        return rgb2lms.rightMult(color);
    }

    public static Vector convertLMS2RGB(Vector color) {
        return lms2rgb.rightMult(color);
    }

}