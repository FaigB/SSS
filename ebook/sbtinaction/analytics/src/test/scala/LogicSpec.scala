package com.preownedkittens

import org.specs2.mutable.Specification

object LogicSpec extends Specification {
	"The 'matchLikelihood' method" should {
		"be 100% when all attributes match" in {
			val tabby = Kitten(1,Set("male","tabby"))
			val prefs = BuyerPreferences(Set("male","tabby"))
			val result = Logic.matchLikelihood(tabby,prefs)
			result must beGreaterThan(.999)
		}	
	
		"be 0% when no attributes match" in {
			val tabby = Kitten(1, Set("male", "tabby"))
			val prefs = BuyerPreferences(Set("female", "calico"))
			val result = Logic.matchLikelihood(tabby, prefs)
			result must beLessThan(.001)
		}
		
		"be 66% when two from three attributes match" in {
			val tabby = Kitten(1, Set("female", "calico", "overweight"))
			val prefs = BuyerPreferences(Set("female", "calico", "thin"))
			val result = Logic.matchLikelihood(tabby, prefs)
			result must beBetween(0.666, 0.667)
		}
	}
}