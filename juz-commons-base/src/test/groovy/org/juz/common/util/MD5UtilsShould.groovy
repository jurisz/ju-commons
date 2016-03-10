package org.juz.common.util

import spock.lang.Specification

class MD5UtilsShould extends Specification {

	def "md5 generation"() {
		expect:
		MD5Utils.md5("ABC") == "902fbdd2b1df0c4f70b4a5d23525e932"
		MD5Utils.md5("gugugu") == "d907be2dc6534bc1ef00939599bc4539"
		MD5Utils.md5("test") == "098f6bcd4621d373cade4e832627b4f6"
	}

	def "md5 upper case"() {
		expect:
		MD5Utils.uppercaseMd5("ABC") == "902FBDD2B1DF0C4F70B4A5D23525E932"
		MD5Utils.uppercaseMd5("gugugu") == "D907BE2DC6534BC1EF00939599BC4539"
	}

	def "md5 with salt"() {
		expect:
		MD5Utils.md5("tohash", "salt", false) == "764992287c6dcc5225c2ab099ab8765a"
	}

	def "with null input"() {
		expect:
		MD5Utils.md5(null) == "d41d8cd98f00b204e9800998ecf8427e"
	}
}
