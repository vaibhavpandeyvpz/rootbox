package com.vaibhavpandey.rootbox.tests;

import com.vaibhavpandey.rootbox.ShellExec;
import com.vaibhavpandey.rootbox.ShellResult;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShellExecTest {

    @Test
    public void testExitCode() {
        ShellExec exec = new ShellExec("sh");
        ShellResult result = exec.run("echo 'VPZ'");
        assertEquals(0, result.getCode());
    }

    @Test
    public void testBadExitCode() {
        ShellExec exec = new ShellExec("sh");
        ShellResult result = exec.run("echo-error 'VPZ'");
        assertEquals(127, result.getCode());
    }

    @Test
    public void testStdOut() {
        ShellExec exec = new ShellExec("sh");
        ShellResult result = exec.run(new String[] { "echo 'VPZ'" }, true);
        assertFalse(result.getOutput().isEmpty());
        assertEquals("VPZ", result.getOutput().get(0));
    }

    @Test
    public void testStdErr() {
        ShellExec exec = new ShellExec("sh");
        ShellResult result = exec.run(new String[] { "echo 'ZPV' >&2" }, false, true);
        assertFalse(result.getError().isEmpty());
        assertEquals("ZPV", result.getError().get(0));
    }
}
