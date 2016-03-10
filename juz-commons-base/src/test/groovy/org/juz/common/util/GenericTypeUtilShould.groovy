package org.juz.common.util

import org.juz.common.api.Command
import org.juz.common.command.CommandHandler
import org.juz.common.command.VoidResponse
import spock.lang.Specification

class GenericTypeUtilShould extends Specification {


	def "simple one level extract"() {
		when:
		Class<?> result = GenericTypeUtil.extractInterfaceFirstTypeArgument(Handler1.class)

		then:
		result == Cmd1.class
	}

	def "extract on extended handler"() {
		when:
		Class<?> result = GenericTypeUtil.extractInterfaceFirstTypeArgument(ExtendedHandler1.class)

		then:
		result == Cmd1.class
	}

	def "error if does not implement interface with params"() {
		when:
		GenericTypeUtil.extractInterfaceFirstTypeArgument(Cmd1.class)

		then:
		thrown IllegalArgumentException
	}

	def "error if does not implement interface"() {
		when:
		GenericTypeUtil.extractInterfaceFirstTypeArgument(Object.class)

		then:
		thrown IllegalArgumentException
	}


	private class Cmd1 implements Command {
	}

	private class Handler1 implements CommandHandler<Cmd1, VoidResponse> {
		@Override
		VoidResponse execute(Cmd1 command) {
			return null
		}
	}

	private class ExtendedHandler1 extends Handler1 {
	}
}
