package io.poddeck.common.configuration;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.apache.commons.configuration2.AbstractConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;

import java.io.File;

@RequiredArgsConstructor(staticName = "create")
public final class ConfigurationInjectionModule extends AbstractModule {
  @Provides
  @Singleton
  AbstractConfiguration provideConfigurationFile() throws Exception {
    return new Configurations().ini(new File("config.ini"));
  }
}
