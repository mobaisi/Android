using s1_Api_Vesion1.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace s1_Api_Vesion1.Controllers
{
    public class ValuesController : ApiController
    {
        // GET api/values
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }

        // GET api/values/5
        public string Get(int id)
        {
            return "value";
        }

        // POST api/values
        public void Post([FromBody]string value)
        {
        }

        // PUT api/values/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/values/5
        public void Delete(int id)
        {
        }

        Session1Entities db = new Session1Entities();

        [HttpPost]
        public IHttpActionResult PostList([FromBody] Finder finder)
        {

            var dt = db.Assets.AsQueryable();

            if (finder.depId!=0)
            {
              dt =  dt.Where(s => s.DepartmentLocation.DepartmentID == finder.depId);
            }

            if (finder.assetGroupId != 0)
            {
            dt =    dt.Where(s => s.AssetGroupID == finder.assetGroupId);
            }

   /*         if (finder.key.Length>2)
            {
                dt.Where(s => s.AssetSN.Contains(finder.key));
                dt.Where(s => s.AssetName.Contains(finder.key));
            }
*/
            DateTime date;
            if (DateTime.TryParse(finder.date2,out date))
            {
             dt =   dt.Where(s => s.WarrantyDate < date);
            }
            return Json(new Result<List<MyAsset>> { code = 200, count = db.Assets.Count(), msg = "ok", data = dt.Select(s => new MyAsset { id = s.ID, assetName = s.AssetName, depName = s.DepartmentLocation.Department.Name,sn = s.AssetSN }).ToList() });
        }


    }


    public class MyAsset {
        public String assetName { get; set; }
        public String  depName{ get; set; }

        public String sn { get; set; }

        public long id { get; set; }



    }
    public class Finder{
        public long depId { get; set; }
        public long assetGroupId { get; set; }

        public String date1 { get; set; }

        public String date2 { get; set; }

        public String key { get; set; }

       /* public int MyProperty { get; set; }*/

    }
}
