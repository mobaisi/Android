using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace s1_Api_Vesion1.Models
{
    public class Result <T>
    {
        public T data { get; set; }

        public int count { get; set; }
        public string msg { get; set; }

        public int code { get; set; }

    }
}