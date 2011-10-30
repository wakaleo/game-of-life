package com.wakaleo.gameoflife.domain

description "This story describes how to translate numbers into roman numerals"

narrative "story description", {
    as a "history student"
    i want "to convert numbers into their roman numeral equivalent"
    so that "I con translate dates on classical inscriptions"
}


examples "simple addition", {
  number  =              [1,   2,    3,     4,    5,    6,    7,     8,      9,    10]
  numberPlusOne  =       [2,   3,    4,     5,    6,    7,    8,     9,     10,    11]
}

scenario "The number #{number} plus 1 should be #{numberPlusOne}", {
    given "the number #number", {
        theNumber = number
    }
    when "the system converts this number to the roman numeral equivalent", {
        theNumberPlusOne = number + 1
    }

    then "the result should be #theNumberPlusOne or #numberPlusTwo", {
        theNumberPlusOne.shouldBe  numberPlusOne
    }
}