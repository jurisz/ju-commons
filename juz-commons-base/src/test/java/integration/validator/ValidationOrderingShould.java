package integration.validator;

import integration.IntegrationTest;
import org.junit.Test;
import org.juz.common.command.CommandValidator;
import org.juz.common.service.service.CommandValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

@Transactional
public class ValidationOrderingShould extends IntegrationTest {

	@Autowired
	private CommandValidatorFactory validatorFactory;

	@Test
	public void validatorsShouldBeOrdered() throws Exception {

		List<CommandValidator<MyTestCommand>> validators = validatorFactory.getValidators(new MyTestCommand());

		assertThat(validators, hasSize(3));
		assertThat(validators.get(0), instanceOf(MyCommandImportantValidator.class));
		assertThat(validators.get(1), instanceOf(MyCommandSomeValidator.class));
		assertThat(validators.get(2), instanceOf(MyCommandLowestValidator.class));
	}
}
