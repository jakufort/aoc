require 'minitest/autorun'
require_relative '../src/day1'

class Day1Test < Minitest::Test
  def test_reads_two_lists
    input = <<~INPUT
      1   3
      5   2
      4   1
    INPUT
    parsed = sorted_input(input)
    assert_equal [1, 4, 5], parsed[:left]
    assert_equal [1, 2, 3], parsed[:right]
  end

  def test_find_differences
    input = <<~INPUT
      1   3
      5   2
      4   1
    INPUT
    parsed = sorted_input(input)

    differences = find_differences(parsed)

    assert_equal [0, 2, 2], differences
  end

  def test_sum_differences
    input = <<~INPUT
      2   6
      1   7
      5   3
    INPUT
    sum = sum_differences(find_differences(sorted_input(input)))

    assert_equal 8, sum
  end

  def test_sum
    left = [10_103, 10_133, 10_172, 10_223, 10_244, 10_465, 10_499]
    right = [10_246, 10_320, 10_397, 10_464, 10_769, 10_870, 10_870]

    sum = sum_differences(find_differences({ left: left, right: right }))

    assert_equal 2097, sum
  end

  def test_group_same_numbers
    grouped = group_same_numbers({ right: [1, 1, 2, 3, 4, 4, 5] })
    expected = { 1 => 2, 2 => 1, 3 => 1, 4 => 2, 5 => 1 }
    assert_equal expected, grouped
  end
end
