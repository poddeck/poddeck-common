package io.poddeck.common.event;

import io.poddeck.common.log.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

final class EventExecutionTest {
  private class ExampleEvent extends Event {
    private final String value;

    public ExampleEvent(String value) {
      this.value = value;
    }

    public String value() {
      return value;
    }
  }

  private class ExampleHook implements Hook {
    @EventHook
    private void example(ExampleEvent event) {
      Assertions.assertEquals(event.value(), "Test");
    }
  }

  @Test
  void testEventExecution() throws Exception {
    var registry = HookRegistry.create();
    var hook = new ExampleHook();
    registry.register(hook);
    var log = Log.create("Test");
    var executor = EventExecutor.create(registry, log);
    executor.executeUncaught(new ExampleEvent("Test"));
  }
}
