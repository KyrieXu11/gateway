package start

import (
	"fmt"
	"gateway/common/config"
	"gateway/common/utils"
	"github.com/stretchr/testify/require"
	"testing"
)

func TestInitModule(t *testing.T) {
	var (
		in0 = "../conf/dev/test.yml"
	)
	err := initModules(in0)
	m := &config.MySQLConfig{}
	err = utils.ParseConfig("mysql", m)
	fmt.Println(m.ConnectString == "", m.DBName, m.Port)
	assertions := require.New(t)
	assertions.Nil(err)
}
