package config

import (
	"fmt"
	"gateway/common/utils"
	"github.com/stretchr/testify/require"
	"testing"
)

func TestInitModule(t *testing.T) {
	var (
		in0 = "../../conf/dev/test.yml"
		in1 = []string{"base", "mysql", "redis"}
	)
	err := initModule(in0, in1)
	fmt.Println(utils.ConfigContextHolder["test"].Get("a.c"))
	assertions := require.New(t)
	assertions.Nil(err)
}
