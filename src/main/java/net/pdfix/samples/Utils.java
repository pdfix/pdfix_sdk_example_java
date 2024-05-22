package net.pdfix.samples;

import net.pdfix.pdfixlib.PdfMatrix;
import net.pdfix.pdfixlib.PdfPoint;
import java.lang.Math;

public class Utils {
  public static float kPi = 3.1415926535897932384626433832795f;

  public static PdfPoint PdfMatrixTransform(PdfMatrix m, PdfPoint p) {
    PdfPoint ret = new PdfPoint();
    ret.x = m.a * p.x + m.c * p.y + m.e;
    ret.y = m.b * p.x + m.d * p.y + m.f;
    return ret;
  }

  public static PdfMatrix PdfMatrixConcat(PdfMatrix m, PdfMatrix m1, Boolean prepend) {
    PdfMatrix left = prepend ? m1 : m;
    PdfMatrix right = prepend ? m : m1;
    m.a = left.a * right.a + left.b * right.c;
    m.b = left.a * right.b + left.b * right.d;
    m.c = left.c * right.a + left.d * right.c;
    m.d = left.c * right.b + left.d * right.d;
    m.e = left.e * right.a + left.f * right.c + right.e;
    m.f = left.e * right.b + left.f * right.d + right.f;
    return m;
  }

  public static PdfMatrix PdfMatrixRotate(PdfMatrix m, float radian, Boolean prepend) {
    float cosValue = (float)Math.cos((double)radian);
    float sinValue = (float)Math.sin((double)radian);
    PdfMatrix m1 = new PdfMatrix();
    m1.a = cosValue;
    m1.b = sinValue;
    m1.c = -sinValue;
    m1.d = cosValue;
    return PdfMatrixConcat(m, m1, prepend);
  }

  public static PdfMatrix PdfMatrixScale(PdfMatrix m, float sx, float sy, Boolean prepend) {
    m.a *= sx;
    m.d *= sy;
    if (prepend) {
      m.b *= sx;
      m.c *= sy;
    }
    m.b *= sy;
    m.c *= sx;
    m.e *= sx;
    m.f *= sy;
    return m;
  }

  public static PdfMatrix PdfMatrixTranslate(PdfMatrix m, float x, float y, Boolean prepend) {
    if (prepend) {
      m.e += x * m.a + y + m.c;
      m.f += y * m.d + x * m.b;
    }
    m.e += x;
    m.f += y; 
    return m;
  }  
}
