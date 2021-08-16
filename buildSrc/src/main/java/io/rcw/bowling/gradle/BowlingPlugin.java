package io.rcw.bowling.gradle;

import io.rcw.bowling.gradle.tasks.GenerateTests;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import javax.annotation.Nonnull;

public final class BowlingPlugin implements Plugin<Project> {
    @Override
    public void apply(@Nonnull Project project) {
        project.getTasks().register("generateTests", GenerateTests.class);
    }
}
