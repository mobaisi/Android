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

        public IHttpActionResult History ([FromBody]IDName obj)
        {


            if (obj!=null)
            {

                return Json(new Result<List<History>>() { code = 200, msg = "ok",data =  
                    db.AssetTransferLogs
                    .Where(s => s.AssetID == obj.id)
                    .Select(s => new History { date = s.TransferDate.ToString(), from1 = s.DepartmentLocation.Department.Name,from2 = s.FromAssetSN,
                     to1 = s.DepartmentLocation1.Department.Name,to2 = s.ToAssetSN
                }).ToList() });

            }
            return Json(new Result<String>() {  code =404 , msg ="Error"});

        }

        [HttpPost]
        
        public IHttpActionResult getCmb([FromBody]IDName name)
        {
            if (name.status ==1)  //get Department Names
            {
                return Json(new Result<List<IDName>>() {
                    code = 200, msg = "1",
                    data = db.Departments
                    .Select(s => new IDName { id = s.ID, name = s.Name }).ToList() });
            }
            else if (name.status==2&&name.id!=null)  // User Department id to get Locations
            {
                return Json(new Result<List<IDName>>()
                {
                    code = 200,
                    msg = "2",
                    data = db.DepartmentLocations
                    .Where(s=>s.DepartmentID ==name.id&&s.EndDate==null)
                   .Select(s => new IDName { id = s.ID, name = s.Location.Name }).ToList()
                });
            }
            else if (name.status==3)
            {
                return Json(new Result<List<IDName>>() ///get AssetGroupNames
                {
                    code = 200,
                    msg = "3",
                    data = db.AssetGroups
                   .Select(s => new IDName { id = s.ID, name = s.Name }).ToList()
                });
            }
            else if (name.status ==4)  //get Start Wiith **  Count 
            {
                return Json(new Result<String>() {count = db.Assets.Where(s => s.AssetSN.StartsWith(name.name)).Count(),code =200, msg="4" });
            }
            else if (name.status==5)
            {
                return Json(new Result<List<IDName>>() ///get AssetGroupNames
                {
                    code = 200,
                    msg = "5",
                    data = db.Employees
    .Select(s => new IDName { id = s.ID, name = s.FirstName +s.LastName }).ToList()
                });

            }


            return Json(new Result<String>() { code = 404, msg = "Error" });

        }


    }

    public class History {
        public string date { get; set; }
        public string from1 { get; set; }
        public string from2 { get; set; }

        public string to1 { get; set; }

        public string to2 { get; set; }
    }


    public class IDName {
        public int status { get; set; }

        public long id { get; set; }
        public String  name { get; set; }

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
