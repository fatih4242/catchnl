//
//  API.swift
//  catchnl
//
//  Created by Fatih Toker on 14.09.2023.
//

import Foundation
import Alamofire

enum URLState: String {
    case BASE_URL = "https://catchnl.tokersoftware.nl"
    case login = "/api/login.php"
    case register = "/api/register.php"
    case resetPassword = "/api/resetPassword.php"
    case uploadComplaint = "/api/addComplaint.php"
    case getComplaints = "/api/getComplaints.php"
    case updateUser = "/api/updateUser.php"
}

class API : ObservableObject {
    
    
    func MakeRequest(endpoint: String, parameters: [String : Any], onSuccess: @escaping(Data) -> Void, onError: @escaping(String) -> Void) {
        
        AF.request(URLState.BASE_URL.rawValue + endpoint , method: .post , parameters: parameters)
            .response(completionHandler: { response in                
                guard let data = response.data else {
                    print("no data")
                    onError("no Data")
                    return
                }
                onSuccess(data)
            })
    }

    
    
}
