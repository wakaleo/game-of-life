scenario 'Adding two integers', {
    given 'two integer values of 1 and 2', {
        a = 1
        b = 2
    }
    when 'we calculate the sum of the two values', {
        sum = a + b
    }
    then 'the sum should be 3', {
        sum.shouldBe 3
    }
}