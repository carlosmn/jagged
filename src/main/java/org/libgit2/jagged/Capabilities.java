package org.libgit2.jagged;

import org.libgit2.jagged.core.NativeMethods;

public class Capabilities
{
    /* Threading is supported */
    public static final Capabilities THREADS = new Capabilities(1 << 0);

    /* SSL (HTTPS) is supported */
    public static final Capabilities HTTPS = new Capabilities(1 << 1);

    /* SSH is supported */
    public static final Capabilities SSH = new Capabilities(1 << 2);

    private static Capabilities instance = null;
    private final static Object instanceLock = new Object();

    public static Capabilities getInstance()
    {
        synchronized (instanceLock)
        {
            if (instance == null)
            {
                instance = new Capabilities(NativeMethods.getCapabilities());
            }

            return instance;
        }
    }

    public static Capabilities combine(Capabilities... capabilities)
    {
        int values = 0;

        for (Capabilities capability : capabilities)
        {
            values |= capability.values;
        }

        return new Capabilities(values);
    }

    private final int values;

    private Capabilities(int values)
    {
        this.values = values;
    }

    public boolean contains(Capabilities cap)
    {
        return ((values & cap.values) == cap.values);
    }
}