from memoryHandler import *
from unittest import TestCase
from io import StringIO
from unittest.mock import patch


class PrintingTest(TestCase):

    def test_allocate_value(self):
        output = allocate("a", "aval", {})
        expected_output = 1
        self.assertEqual(output, expected_output)

    def test_allocate_value_allocated(self):
        output = allocate("a", "aval", {"a": ptr("aval1")})
        expected_output = 0
        self.assertEqual(output, expected_output)

    def test_allocate_value_free(self):
        output = allocate("a", "aval", {"a": ptr(None)})
        expected_output = 1
        self.assertEqual(output, expected_output)

    def test_assign_value(self):
        output = assign("a", "b", {"b": ptr("bval")})
        expected_output = 1
        self.assertEqual(output, expected_output)
    
    def test_assign_value_two(self):
        output = assign("a", "b", {"a": ptr("aval1"), "b": ptr("bval")})
        expected_output = 1
        self.assertEqual(output, expected_output)
    
    def test_assign_value_no_exist(self):
        output = assign("a", "b", {"a": ptr("aval1")})
        expected_output = 0
        self.assertEqual(output, expected_output)

    def test_assign_value_no_allocated(self):
        output = assign("a", "b", {})
        expected_output = 0
        self.assertEqual(output, expected_output)

    def test_assign_value_none(self):
        output = assign("a", "b", { "b": ptr(None)})
        expected_output = 0
        self.assertEqual(output, expected_output)

    def test_assign_value_none(self):
        output = assign("a", "b", {"a": ptr("aval1"), "b": ptr(None)})
        expected_output = 0
        self.assertEqual(output, expected_output)

    def test_print_value(self):
        output = printPtr("a", {"a": ptr("aval1"), "b": ptr(None)})
        expected_output = 1
        self.assertEqual(output, expected_output)
    
    def test_print_value_none(self):
        output = printPtr("b", {"a": ptr("aval1"), "b": ptr(None)})
        expected_output = 0
        self.assertEqual(output, expected_output)

    def test_print_value_no_allocated(self):
        output = printPtr("b", {"a": ptr("aval1")})
        expected_output = 0
        self.assertEqual(output, expected_output)

    def test_free_value(self):
        output = free("a", {"a": ptr("aval1")})
        expected_output = 1
        self.assertEqual(output, expected_output)

    def test_free_value_none(self):
        output = free("a", {"a": ptr(None)})
        expected_output = 0
        self.assertEqual(output, expected_output)

    def test_free_value_no_allocated(self):
        output = free("b", {"a": ptr("aval1")})
        expected_output = 0
        self.assertEqual(output, expected_output)