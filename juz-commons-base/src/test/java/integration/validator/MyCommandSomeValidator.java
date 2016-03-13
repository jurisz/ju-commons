package integration.validator;

import com.google.common.collect.Sets;
import org.juz.common.api.ValidationError;
import org.juz.common.command.CommandValidator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Order(0)
public class MyCommandSomeValidator implements CommandValidator<MyTestCommand> {

	@Override
	public Set<ValidationError> validate(MyTestCommand command) {
		return Sets.newHashSet();
	}
}
