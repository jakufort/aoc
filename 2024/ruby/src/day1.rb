# frozen_string_literal: true

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
