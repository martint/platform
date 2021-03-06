package com.proofpoint.node;

import com.google.common.net.InetAddresses;
import com.proofpoint.testing.Assertions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.InetAddress;

public class TestNodeInfo
{
    public static final String ENVIRONMENT = "environment_1234";
    public static final String POOL = "pool_1234";

    @Test
    public void testBasicNodeInfo()
    {
        long testStartTime = System.currentTimeMillis();

        String nodeId = "nodeId";
        String location = "location";
        String binarySpec = "binary";
        String configSpec = "config";
        InetAddress internalIp = InetAddresses.forString("10.0.0.22");
        InetAddress bindIp = InetAddresses.forString("10.0.0.33");
        String externalAddress = "external";

        NodeInfo nodeInfo = new NodeInfo(ENVIRONMENT, POOL, nodeId, internalIp, bindIp, externalAddress, location, binarySpec, configSpec);
        Assert.assertEquals(nodeInfo.getEnvironment(), ENVIRONMENT);
        Assert.assertEquals(nodeInfo.getPool(), POOL);
        Assert.assertEquals(nodeInfo.getNodeId(), nodeId);
        Assert.assertEquals(nodeInfo.getLocation(), location);
        Assert.assertEquals(nodeInfo.getBinarySpec(), binarySpec);
        Assert.assertEquals(nodeInfo.getConfigSpec(), configSpec);
        Assert.assertNotNull(nodeInfo.getInstanceId());

        Assertions.assertNotEquals(nodeInfo.getNodeId(), nodeInfo.getInstanceId());

        Assert.assertEquals(nodeInfo.getInternalIp(), internalIp);
        Assert.assertEquals(nodeInfo.getExternalAddress(), externalAddress);
        Assert.assertEquals(nodeInfo.getBindIp(), bindIp);
        Assertions.assertGreaterThanOrEqual(nodeInfo.getStartTime(), testStartTime);

        // make sure toString doesn't throw an exception
        Assert.assertNotNull(nodeInfo.toString());
    }

    @Test
    public void testDefaultAddresses()
    {
        NodeInfo nodeInfo = new NodeInfo(ENVIRONMENT, POOL, "nodeInfo", InetAddresses.forString("10.0.0.22"), null, null, null, null, null);
        Assert.assertEquals(nodeInfo.getExternalAddress(), "10.0.0.22");
        Assert.assertEquals(nodeInfo.getBindIp(), InetAddresses.forString("0.0.0.0"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidEnvironment()
    {
        new NodeInfo("ENV", POOL, null, null, null, null, null, null, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidPool()
    {
        new NodeInfo(ENVIRONMENT, "POOL", null, null, null, null, null, null, null);
    }
}
