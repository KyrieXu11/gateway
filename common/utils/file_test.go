package utils

import (
	"github.com/stretchr/testify/require"
	"testing"
)

func TestCheckFile(t *testing.T) {
	var (
		in  = "base 1.json"
		out = true
	)
	actual := checkFile(in)
	assertions := require.New(t)
	assertions.Equal(out, actual)
}
