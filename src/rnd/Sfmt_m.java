package rnd;
/*
Sfmt_m.java �������C�u����(216091)
coded by isaku@pb4.so-net.ne.jp
*/

class Sfmt_m
{
    static Sfmt[]z={
        new Sfmt(  1),
        new Sfmt( -1),
        new Sfmt( -2),
        new Sfmt( -3),
        new Sfmt( -4),
        new Sfmt( -5),
        new Sfmt( -6),
        new Sfmt( -7),
        new Sfmt( -8),
        new Sfmt( -9),
        new Sfmt(-10),
        new Sfmt(-11),
        new Sfmt(-12),
        new Sfmt(-13),
        new Sfmt(-14),
        new Sfmt(-15),
        new Sfmt(-16),
        new Sfmt(-17),
        new Sfmt(-18),
        new Sfmt(-19),
        new Sfmt(-20),
        new Sfmt(-21),
        new Sfmt(-22),
        new Sfmt(-23),
        new Sfmt(-24),
        new Sfmt(-25),
        new Sfmt(-26),
        new Sfmt(-27),
        new Sfmt(-28),
        new Sfmt(-29),
        new Sfmt(-30),
        new Sfmt(-31)
    };

    public static String IdString_m(int i)
    { return z[i].IdString(); }

    public static void InitMt_m(int i,int s)
    { z[i].InitMt(s); }

    public static void InitMtEx_m(int i,int[]k)
    { z[i].InitMtEx(k); }

    public static int NextMt_m(int i)
    { return z[i].NextMt(); }

    public static int NextInt_m(int i,int n)
    { return z[i].NextInt(n); }

    public static int NextBit_m(int i)
    { return z[i].NextBit(); }

    public static int NextByte_m(int i)
    { return z[i].NextByte(); }

    public static double NextUnif_m(int i)
    { return z[i].NextUnif(); }

    public static int NextIntEx_m(int i,int r)
    { return z[i].NextIntEx(r); }

    public static double NextChisq_m(int i,double n)
    { return z[i].NextChisq(n); }

    public static double NextGamma_m(int i,double a)
    { return z[i].NextGamma(a); }

    public static int NextGeometric_m(int i,double p)
    { return z[i].NextGeometric(p); }

    public static double NextTriangle_m(int i)
    { return z[i].NextTriangle(); }

    public static double NextExp_m(int i)
    { return z[i].NextExp(); }

    public static double NextNormal_m(int i)
    { return z[i].NextNormal(); }

    public static double[]NextUnitVect_m(int i,int n)
    { return z[i].NextUnitVect(n); }

    public static int NextBinomial_m(int i,int n,double p)
    { return z[i].NextBinomial(n,p); }

    public static double[]NextBinormal_m(int i,double r)
    { return z[i].NextBinormal(r); }

    public static double NextBeta_m(int i,double a,double b)
    { return z[i].NextBeta(a,b); }

    public static double NextPower_m(int i,double n)
    { return z[i].NextPower(n); }

    public static double NextLogistic_m(int i)
    { return z[i].NextLogistic(); }

    public static double NextCauchy_m(int i)
    { return z[i].NextCauchy(); }

    public static int NextPoisson_m(int i,double l)
    { return z[i].NextPoisson(l); }

    public static double NextFDist_m(int i,double n1,double n2)
    { return z[i].NextFDist(n1,n2); }

    public static double NextTDist_m(int i,double n)
    { return z[i].NextTDist(n); }

    public static double NextWeibull_m(int i,double a)
    { return z[i].NextWeibull(a); }

}
