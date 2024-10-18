package org.main_java.deNexus_project.util;


public class SampleNotFoundException extends RuntimeException {
    public SampleNotFoundException(Long id) {
        super("Sample not found with id " + id);
    }
}
