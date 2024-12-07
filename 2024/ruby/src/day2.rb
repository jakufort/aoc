require 'minitest/autorun'

def read_reports(input)
  input.split("\n")
    .map { |line| line.split(" ").map(&:to_i) }
end

def levels_safe?(first_elements_difference, left, right)
  diff = left - right
  diff != 0 and ((first_elements_difference < 0) == (diff < 0)) and diff.abs <= 3
end

def report_safe?(report)
  difference = report[0] - report[1]
  report.each_cons(2).all? { |pair| levels_safe?(difference, *pair) }
end

def drop_at(array, index)
  array.dup.tap { |x| x.delete_at(index) }
end

def dampened_report_safe?(report)
  difference = report[0] - report[1]

  for i in 1..(report.size - 1)
    left = report[i - 1]
    right = report[i]
    if !levels_safe?(difference, left, right)
      if i == (report.size - 1)
        return true
      end
      safe = (report_safe?(drop_at(report, i - 1)) or report_safe?(drop_at(report, i)))
      if (i > 1)
        return (safe or report_safe?(drop_at(report, i - 2)))
      end
      return safe
    end
  end
  true
end

def count_safe_reports(reports, predicate)
  reports.map(&method(predicate)).filter { |x| x }.count
end

input = File.open('input/day2').read

reports = read_reports(input)

puts "part 1: #{count_safe_reports(reports, :report_safe?)}"
puts "part 2: #{count_safe_reports(reports, :dampened_report_safe?)}"

class Day2Test < Minitest::Test 
  def test_report_read
    input = <<~INPUT
      7 6 4 2 1
      1 2 7 8 9
    INPUT

    reports = read_reports(input)

    assert_equal [[7, 6, 4, 2, 1], [1, 2, 7, 8, 9]], reports
  end

  def test_report_safe?
    assert_equal true, report_safe?([1, 3, 6, 7, 9]), "[1, 3, 6, 7, 9]"
    assert_equal true, report_safe?([7, 6, 4, 2, 1]), "[7, 6, 4, 2, 1]"
    assert_equal false, report_safe?([1, 2, 7, 8, 9]), "[1, 2, 7, 8, 9]"
    assert_equal false, report_safe?([9, 7, 6, 2, 1]), "[9, 7, 6, 2, 1]"
    assert_equal false, report_safe?([1, 3, 2, 4, 5]), "[1, 3, 2, 4, 5]"
    assert_equal false, report_safe?([8, 6, 4, 4, 1]), "[8, 6, 4, 4, 1]"
    assert_equal false, report_safe?([50, 49, 50, 51, 52, 54, 55]), "[50, 49, 50, 51, 52, 54, 55]"
  end

  def test_dampened_report_safe?
    assert_equal true, dampened_report_safe?([1, 3, 6, 7, 9]), "[1, 3, 6, 7, 9]"
    assert_equal true, dampened_report_safe?([7, 6, 4, 2, 1]), "[7, 6, 4, 2, 1]"
    assert_equal false, dampened_report_safe?([1, 2, 7, 8, 9]), "[1, 2, 7, 8, 9]"
    assert_equal false, dampened_report_safe?([9, 7, 6, 2, 1]), "[9, 7, 6, 2, 1]"
    assert_equal true, dampened_report_safe?([1, 3, 2, 4, 5]), "[1, 3, 2, 4, 5]"
    assert_equal true, dampened_report_safe?([8, 6, 4, 4, 1]), "[8, 6, 4, 4, 1]"
    assert_equal true, dampened_report_safe?([8, 10, 13, 14, 12]), "[8, 10, 13, 14, 12]"
    assert_equal true, dampened_report_safe?([9, 7, 8, 10, 13, 16, 17]), "[9, 7, 8, 10, 13, 16, 15]"
  end
end