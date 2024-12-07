# frozen_string_literal: true
require 'minitest/autorun' 

def sorted_input(input)
  left = []
  right = []
  input
    .split("\n")
    .map { |line| line.split(' ').map(&:to_i) }
    .each do |vec|
      left.push(vec[0])
      right.push(vec[1])
    end

  { left: left.sort, right: right.sort }
end

def find_differences(parsed)
  differences = []
  parsed[:left].each_with_index do |a, index|
    differences.push((parsed[:right][index] - a).abs)
  end
  differences
end

def sum_differences(parsed)
  find_differences(parsed).sum
end

def group_same_numbers(parsed_input)
  parsed_input[:right].each_with_object(Hash.new(0)) do |e, h|
    h[e] += 1
  end
end

def similarity(parsed_input)
  grouped = group_same_numbers(parsed_input)
  parsed_input[:left].reduce(0) do |sum, n|
    sum + (n * grouped.fetch(n, 0))
  end
end

input = File.open('input/day1').read

parsed = sorted_input(input)

puts "part one: #{sum_differences(parsed)}"

puts "part one: #{similarity(parsed)}"



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
    sum = sum_differences(sorted_input(input))

    assert_equal 8, sum
  end

  def test_sum
    left = [10_103, 10_133, 10_172, 10_223, 10_244, 10_465, 10_499]
    right = [10_246, 10_320, 10_397, 10_464, 10_769, 10_870, 10_870]

    sum = sum_differences({ left: left, right: right })

    assert_equal 2097, sum
  end

  def test_group_same_numbers
    grouped = group_same_numbers({ right: [1, 1, 2, 3, 4, 4, 5] })
    expected = { 1 => 2, 2 => 1, 3 => 1, 4 => 2, 5 => 1 }
    assert_equal expected, grouped
  end
end
